import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

const CropStockListener = () => {
    const [stock, setStock] = useState(null);

    useEffect(() => {
        // Initialize SockJS and STOMP client
        const socket = new SockJS('http://localhost:8080/ws');
        const stompClient = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                console.log('Connected to WebSocket');

                // Subscribe to the stock update topic
                stompClient.subscribe('/topic/stockUpdate', (message) => {
                    const newStock = JSON.parse(message.body);
                    console.log('Received stock update:', newStock);
                    setStock(newStock);
                });
            },
            onStompError: (frame) => {
                console.error('Broker reported error: ' + frame.headers['message']);
                console.error('Additional details: ' + frame.body);
            },
        });

        stompClient.activate();

        // Cleanup on unmount
        return () => {
            if (stompClient.active) {
                stompClient.deactivate();
            }
        };
    }, []);

    return (
        <div style={{ padding: '20px', borderRadius: '8px', background: '#f0f4f8' }}>
            <h3>Real-time Crop Stock Update</h3>
            {stock !== null ? (
                <p>Latest Stock Level: <strong>{stock}</strong></p>
            ) : (
                <p>Waiting for updates...</p>
            )}
        </div>
    );
};

export default CropStockListener;
