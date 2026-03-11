import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

const LiveStockTicker = () => {
    const [updates, setUpdates] = useState([]);

    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/ws');
        const stompClient = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                stompClient.subscribe('/topic/stockUpdate', (message) => {
                    const newStock = JSON.parse(message.body);
                    setUpdates(prev => [
                        { id: Date.now(), ...newStock },
                        ...prev.slice(0, 4)
                    ]);
                });
            },
        });

        stompClient.activate();
        return () => stompClient.deactivate();
    }, []);

    return (
        <div className="bg-blue-900 text-white p-4 rounded-2xl shadow-lg overflow-hidden">
            <h3 className="text-xs font-bold uppercase tracking-wider text-blue-300 mb-2 flex items-center">
                <span className="w-2 h-2 bg-red-500 rounded-full mr-2 animate-pulse"></span>
                Live Stock Updates
            </h3>
            <div className="space-y-2">
                {updates.length > 0 ? updates.map(update => (
                    <div key={update.id} className="flex justify-between items-center text-sm border-b border-blue-800 pb-1 animate-slide-in">
                        <span className="font-medium">Crop Stock Sync</span>
                        <span className="text-blue-200">Value: {update}</span>
                    </div>
                )) : (
                    <p className="text-xs text-blue-400 italic">Listening for live trades...</p>
                )}
            </div>
        </div>
    );
};

export default LiveStockTicker;
