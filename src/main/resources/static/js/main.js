'use strict';


const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const connectingElement = document.querySelector('.connecting');
const chatArea = document.querySelector('#chat-messages');
const nickname = document.getElementById('fullname').textContent;
const selectedUserId =  document.getElementById('receiver').textContent;
let stompClient = null;



function connect(event) {

    const socket = new SockJS('/ws');

    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    event.preventDefault();
}


function onConnected(options) {
    console.log('Connected to WebSocket server');
    stompClient.subscribe(`/user/${nickname}/queue/messages`, onMessageReceived);
    console.log(`Subscribed to /user/${nickname}/queue/messages`);
    stompClient.subscribe(`/user/public`, onMessageReceived);
    console.log('Subscribed to /user/public');
    fetchAndDisplayUserChat().then();
}
function displayMessage(senderId, content) {
    const messageContainer = document.createElement('div');
    messageContainer.classList.add('message');
    if (senderId === nickname) {
        messageContainer.classList.add('sender');
    } else {
        messageContainer.classList.add('receiver');
    }
    const message = document.createElement('p');
    message.textContent = content;
    messageContainer.appendChild(message);
    chatArea.appendChild(messageContainer);
}

async function fetchAndDisplayUserChat() {
    const userChatResponse = await fetch(`/messages/${nickname}/${selectedUserId}`);
    const userChat = await userChatResponse.json();
    chatArea.innerHTML = '';
    userChat.forEach(chat => {
        displayMessage(chat.senderId, chat.text);
    });
    chatArea.scrollTop = chatArea.scrollHeight;
}


function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            senderId: nickname,
            recipientId: selectedUserId,
            text: messageInput.value.trim(),
            timestamp: new Date()
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        displayMessage(nickname, messageInput.value.trim());
        messageInput.value = '';
    }
    chatArea.scrollTop = chatArea.scrollHeight;
    event.preventDefault();
}


async function onMessageReceived(payload) {

    console.log('Message received', payload);

    const message = JSON.parse(payload.body);
    console.log(message.senderId);
    console.log(message.text);
    if (selectedUserId && selectedUserId === message.senderId) {
        displayMessage(message.senderId, message.text);
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    const notifiedUser = document.getElementById(message.senderId);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
    }
}


document.addEventListener('DOMContentLoaded', function() {

    connect();
});
messageForm.addEventListener('submit', sendMessage, true);
