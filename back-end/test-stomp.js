// Node.js WebSocket + STOMP test for /notification/react
// Run with: npm init -y && npm install ws @stomp/stompjs && node test-stomp.js

const WebSocket = require('ws');
const { Client } = require('@stomp/stompjs');

// ==== CONFIGURATION ====
const WS_URL = 'ws://localhost:7070/web-socket-kindred';
const args = process.argv.slice(2);

if (args.length < 2) {
    console.error("Usage: node test-stomp.js <JWT> <RECEIVER_ID> [CHAT_ID] [MESSAGE_TEXT]");
    process.exit(1);
}

const JWT = args[0].startsWith('Bearer ') ? args[0] : 'Bearer ' + args[0];
const RECEIVER_ID = parseInt(args[1], 10);
const CHAT_ID = args[2] ? parseInt(args[2], 10) : 1; // Default chat ID 1
const MSG_TEXT = args[3] || "Hello from test-stomp.js";

console.log(`Config: Receiver=${RECEIVER_ID}, Chat=${CHAT_ID}, Text="${MSG_TEXT}"`);
// =======================

const connectHeaders = {};
if (JWT) connectHeaders.Authorization = JWT;

const client = new Client({
    webSocketFactory: () => new WebSocket(WS_URL),
    connectHeaders,
    debug: (msg) => {
        // Filter out PING/PONG to reduce noise if desired, currently logging everything with [STOMP]
        // console.log('[STOMP]', msg); 
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
});

client.onConnect = (frame) => {
    console.log('Connected to STOMP broker.');

    // Subscribe to Chat
    client.subscribe('/user/listener/chat', (message) => {
        console.log('Received on /user/listener/chat:', message.body);
    });
    console.log('Subscribed to /user/listener/chat');

    // Subscribe to Notification (Keeping existing one)
    client.subscribe('/user/listener/notification', (message) => {
        console.log('Received on /user/listener/notification:', message.body);
    });
    console.log('Subscribed to /user/listener/notification');

    // Send a test message
    const chatMessage = {
        text: MSG_TEXT,
        chat_id: CHAT_ID,
        receiver_id: RECEIVER_ID
    };

    client.publish({
        destination: '/app/message',
        body: JSON.stringify(chatMessage)
    });
    console.log('Sent message to /app/message:', chatMessage);

    console.log('Press Ctrl+C to exit.');
};

client.onStompError = (frame) => {
    console.error('STOMP error:', frame.headers['message']);
};

client.activate();
