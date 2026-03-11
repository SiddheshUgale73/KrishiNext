# KrishiNext Run Guide

Follow these steps to set up and run the KrishiNext platform (React + Spring Boot) on your local machine.

---

## 📋 Prerequisites
Confirm you have the following installed:
- **Java 17 or higher**
- **Node.js (v18+) & npm**
- **Maven**
- **MongoDB** (Local instance or MongoDB Atlas URI)
- **Python 3.x** (Required for CropSense AI feature)

---

## 🛠️ 1. Backend Setup (Spring Boot)

1. **Navigate to the backend directory**:
   ```bash
   cd backend
   ```

2. **Configure Environment Variables**:
   Open `src/main/resources/application.properties` and update the following:
   - `spring.data.mongodb.uri`: Your MongoDB connection string.
   - `jwt.secret`: A secure random string for token signing.
   - `gemini.api.key`: Your Google Gemini API key.

3. **Install Dependencies & Build**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
   *The backend will start on `http://localhost:8080`.*

---

## 💻 2. Frontend Setup (React)

1. **Navigate to the frontend directory**:
   ```bash
   cd ../frontend
   ```

2. **Install Dependencies**:
   ```bash
   npm install
   ```

3. **Configure Environment**:
   Ensure `.env` contains:
   ```env
   VITE_API_BASE_URL=http://localhost:8080
   ```

4. **Start the Development Server**:
   ```bash
   npm run dev
   ```
   *The frontend will be available at `http://localhost:5173` (or the port shown in your terminal).*

---

## 🤖 3. AI Module Setup (Python)

The **CropSense AI** feature requires a Python environment.
- Ensure `python` is in your system PATH.
- The backend will automatically execute `scripts/cropsense_predict.py` when you request a prediction from the dashboard.

---

## 🔄 Real-time Features (WebSocket)
- The platform uses STOMP over WebSocket.
- Confirm your browser console shows "Connected to WebSocket" after logging in.
- Note: This feature works best with a direct browser connection (Vercel may block WebSockets in production).

---

## 🚀 Troubleshooting
- **'mvn' is not recognized**: I have automatically triggered a Maven installation for you using `winget`. **Please restart your terminal or VS Code** to refresh the system PATH, then try running the command again.
- **CORS Errors**: Ensure the `setAllowedOrigins("*")` in `WebSocketConfig.java` and `api.js` base URL match your port.
- **MongoDB Connection**: Verify your IP is whitelisted in MongoDB Atlas if using a cloud database.
- **Python Path**: If the AI prediction fails, ensure `python` (not `python3`) is the command used in `CropSenseService.java` or update it to match your system.
