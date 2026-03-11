import React, { useState } from "react";
import Heading from "../../components/heading/Heading";
import Spinner from "../../components/loading/Spinner";
import api from "../../utils/api";
import InputTag from "../../components/input/InputTag";

const CropSenseAI = () => {
  const [prediction, setPrediction] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const [formData, setFormData] = useState({
    cropName: "",
    marketName: "",
    region: "",
    currentPrice: "",
    demandScore: 50,
  });

  const getAIPrediction = async () => {
    try {
      setIsLoading(true);
      const res = await api.getPricePrediction({
        ...formData,
        currentPrice: parseFloat(formData.currentPrice),
      });
      setPrediction(res);
    } catch (err) {
      console.error("Prediction failed:", err);
      setPrediction(null);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <Heading text={"CropSense AI Price Predictor"} textAlign="text-left" />
      <div className="container max-w-screen-lg mx-auto">
        <div className="bg-white p-8 rounded-2xl shadow-lg border border-gray-100">
          <div className="grid gap-8 grid-cols-1 lg:grid-cols-2">
            <div>
              <h3 className="text-lg font-semibold mb-4 text-green-700">Market Data Input</h3>
              <form
                className="space-y-4"
                onSubmit={(e) => {
                  e.preventDefault();
                  getAIPrediction();
                }}
              >
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <InputTag label="Crop Name" placeholder="e.g. Wheat" value={formData.cropName} setFormData={setFormData} toUpdate="cropName" />
                  <InputTag label="Market Name" placeholder="e.g. Krishi Mandi" value={formData.marketName} setFormData={setFormData} toUpdate="marketName" />
                </div>

                <InputTag label="Region" placeholder="e.g. Maharashtra" value={formData.region} setFormData={setFormData} toUpdate="region" />

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <InputTag label="Current Price (Rs/Unit)" type="number" value={formData.currentPrice} setFormData={setFormData} toUpdate="currentPrice" />
                  <div className="flex flex-col">
                    <label className="text-sm font-medium text-gray-700">Demand Score (1-100)</label>
                    <input
                      type="range"
                      min="1" max="100"
                      value={formData.demandScore}
                      className="mt-2 accent-green-600"
                      onChange={(e) => setFormData({ ...formData, demandScore: parseInt(e.target.value) })}
                    />
                    <span className="text-xs text-right text-gray-500">{formData.demandScore}</span>
                  </div>
                </div>

                <button
                  type="submit"
                  disabled={isLoading}
                  className={`w-full py-3 px-4 rounded-xl font-bold text-white transition-all ${isLoading
                      ? "bg-gray-400 cursor-not-allowed"
                      : "bg-green-600 hover:bg-green-700 shadow-md hover:shadow-lg"
                    }`}
                >
                  {isLoading ? <Spinner width="w-5" color="#ffffff" /> : "Run AI Prediction"}
                </button>
              </form>
            </div>

            <div className="bg-gray-50 p-6 rounded-2xl border border-dashed border-gray-200">
              <h3 className="text-lg font-semibold mb-4 text-gray-700">AI Analysis Result</h3>
              {prediction ? (
                <div className="space-y-6">
                  <div className="flex justify-between items-center">
                    <p className="text-gray-600">Predicted Price</p>
                    <p className="text-2xl font-bold text-green-700">Rs. {prediction.predictedPrice}</p>
                  </div>

                  <div className="flex justify-between items-center">
                    <p className="text-gray-600">Expected Trend</p>
                    <span className={`px-3 py-1 rounded-full text-xs font-bold ${prediction.trend === 'Up' ? 'bg-green-100 text-green-700' :
                        prediction.trend === 'Down' ? 'bg-red-100 text-red-700' :
                          'bg-blue-100 text-blue-700'
                      }`}>
                      {prediction.trend}
                    </span>
                  </div>

                  <div className="p-4 bg-white rounded-xl border border-gray-100 italic text-sm text-gray-600">
                    "{prediction.marketInsight}"
                  </div>

                  <div className="text-center pt-4">
                    <p className="text-xs text-gray-400">AI Confidence: {prediction.confidenceScore}</p>
                  </div>
                </div>
              ) : (
                <div className="flex flex-col items-center justify-center h-full text-gray-400 py-12">
                  <p>No prediction data yet.</p>
                  <p className="text-xs">Fill the form and click 'Run AI Prediction'</p>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default CropSenseAI;
