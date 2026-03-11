import React, { useState, useEffect } from "react";
import Heading from "../../components/heading/Heading";
import AreaGraph from "../../components/graphs/AreaGraph";
import BarGraph from "../../components/graphs/BarGraph";
import TradeVolumeChart from "../../components/graphs/TradeVolumeChart";
import RevenueChart from "../../components/graphs/RevenueChart";
import LiveStockTicker from "../../components/graphs/LiveStockTicker";
import GraphSkeleton from "../../components/skeleton/GraphSkeleton";
import EmptyStateText from "../../components/empty_state/EmptyStateText";
import api from "../../utils/api";

function SellerOverview() {
  const [data, setData] = useState({
    mostTraded: [],
    revenue: [],
    demand: [],
    totalListings: 0
  });
  const [isLoading, setIsLoading] = useState(true);

  const fetchAnalytics = async () => {
    try {
      setIsLoading(true);
      const [mostTraded, revenue, demand, totalListings] = await Promise.all([
        api.getMostTradedCrops(),
        api.getSellerRevenue(),
        api.getMostTradedCrops(), // Reusing or adding demand logic
        api.getTotalListings()
      ]);

      setData({
        mostTraded,
        revenue,
        demand,
        totalListings
      });
    } catch (error) {
      console.error("Failed to fetch analytics:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchAnalytics();
  }, []);

  return (
    <>
      <Heading text={"Platform Insights"} textAlign="text-left" />
      {isLoading ? (
        <GraphSkeleton noOfBoxes={2} />
      ) : data.mostTraded.length === 0 ? (
        <EmptyStateText text="No data available yet. Start trading to see insights!" />
      ) : (
        <div className="space-y-8">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="bg-green-50 p-6 rounded-2xl border border-green-100">
              <p className="text-sm text-green-600 font-medium">Total Listings</p>
              <h3 className="text-3xl font-bold text-green-900">{data.totalListings}</h3>
            </div>
            <div className="md:col-span-2">
              <LiveStockTicker />
            </div>
          </div>

          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
            <TradeVolumeChart data={data.mostTraded} />
            <RevenueChart data={data.revenue} title="Top Seller Revenue" />
          </div>

          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
            {/* Legacy graphs or additional demand charts */}
            <BarGraph
              title="Historical Demand"
              data={data.demand}
              color="#A3DC9A"
              xKey="cropName"
              yKey="totalQuantity"
            />
          </div>
        </div>
      )}
    </>
  );
}

export default SellerOverview;
