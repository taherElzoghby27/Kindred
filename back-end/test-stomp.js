// Node.js WebSocket + STOMP test for /notification/react
// Run with: npm init -y && npm install ws @stomp/stompjs && node test-stomp.js

const WebSocket = require('ws');
const { Client } = require('@stomp/stompjs');

// ==== CONFIGURATION ====
const WS_URL = 'ws://localhost:7070/web-socket-kindred';
const JWT = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1lZE1vaGFtZWQ4OTdAZ21haWwuY29tIiwiaWF0IjoxNzY5MjkyMjg1LCJleHAiOjE3NzE4ODQyODV9.yGV7lXe0bWg7FOARaVPaDCMZg2Xn9Jwjxk0fIu7g2CE'; // Paste your Bearer token here, e.g., 'Bearer eyJhbGciOiJIUzI1NiJ9...'
// =======================

const connectHeaders = {};
if (JWT) connectHeaders.Authorization = JWT;

const client = new Client({
    webSocketFactory: () => new WebSocket(WS_URL),
    connectHeaders,
    debug: (msg) => console.log('[STOMP]', msg),
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
});

client.onConnect = (frame) => {
    console.log('Connected to STOMP broker.');
    client.subscribe('/user/listener/notification', (message) => {
        console.log('Received on /user/listener/notification:', message.body);
    });
    console.log('Subscribed to /user/listener/notification');
    console.log('Press Ctrl+C to exit.');
};

client.onStompError = (frame) => {
    console.error('STOMP error:', frame.headers['message']);
};

client.activate();
