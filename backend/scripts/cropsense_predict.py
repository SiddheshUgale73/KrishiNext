import sys
import json
import random

def predict_price(data):
    # This is a sample AI model simulation
    # In a real scenario, you would load a pickle/h5 model here
    crop_name = data.get('cropName', 'Unknown')
    current_price = data.get('currentPrice', 100.0)
    
    # Simple logic to simulate prediction
    prediction_factor = random.uniform(0.9, 1.2)
    predicted_price = current_price * prediction_factor
    
    trend = "Stable"
    if predicted_price > current_price * 1.05:
        trend = "Up"
    elif predicted_price < current_price * 0.95:
        trend = "Down"
        
    result = {
        "predictedPrice": round(predicted_price, 2),
        "trend": trend,
        "confidenceScore": f"{random.randint(70, 95)}%",
        "marketInsight": f"Predicted market behavior for {crop_name} based on regional demand factors."
    }
    return result

if __name__ == "__main__":
    if len(sys.argv) > 1:
        input_data = json.loads(sys.argv[1])
        prediction = predict_price(input_data)
        print(json.dumps(prediction))
    else:
        print(json.dumps({"error": "No input data provided"}))
