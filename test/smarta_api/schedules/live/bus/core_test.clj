(ns smarta-api.schedules.live.bus.core-test
  (:require [smarta-api.schedules.live.bus.core :refer :all]
            [cheshire.core :refer :all]
            [clj-http.client :as c])
  (:use [clojure.test]
        [clj-http.fake]))

(def bus-list-endpoint "http://developer.itsmarta.com/BRDRestService/RestBusRealTimeService/GetAllBus?apiKey=some-api-key")

(def bus-list-response-body
  '({:adherence "-23"
     :blockid "144"
     :block_abbr "143-17"
     :direction "Northbound"
     :latitude "34.0591387"
     :longitude "-84.2430457"
     :msgtime "4/24/2019 9:05:29 PM"
     :route "100"
     :stopid "1"
     :timepoint "Timepoint 1"
     :tripid "6708735"
     :vehicle "2504"}
    {:adherence "-23"
     :blockid "144"
     :block_abbr "143-17"
     :direction "Northbound"
     :latitude "34.0591387"
     :longitude "-84.2430457"
     :msgtime "4/24/2019 9:05:29 PM"
     :route "100"
     :stopid "2"
     :timepoint "Timepoint 2"
     :tripid "6708735"
     :vehicle "2504"}))

(defn get-bus-list-mock [request]
  {:status 200 :headers {} :body (generate-string bus-list-response-body)})

(deftest bus-operations
  (with-fake-routes {bus-list-endpoint get-bus-list-mock}
    (testing "details"
      (let [expected-detail-count 2
            details (get-bus-details)]
        (is (= expected-detail-count (count details)))))
    (testing "routes"
      (testing "all routes"
        (let [expected-route-count 1
              expected-routes ["100"]
              bus-routes (get-bus-routes)]
          (is (= expected-route-count (count bus-routes)))
          (is (= expected-routes bus-routes)))))
    (testing "by stop"
      (let [expected-route-count 1
            bus-routes (get-routes-by-stop "1")]
        (is (= expected-route-count (count bus-routes)))))
    (testing "timepoints"
      (let [expected-timepoint-count 2
            expected-timepoints ["Timepoint 1" "Timepoint 2"]
            bus-timepoints (get-bus-timepoints)]
        (is (= expected-timepoint-count (count bus-timepoints)))
        (is (= expected-timepoints bus-timepoints))))
    (testing "stops"
      (testing "all stops"
        (let [expected-stop-count 2
              stops (get-bus-stops)]
          (is (= expected-stop-count (count stops)))))
      (testing "by route"
        (let [expected-stop-count 2
              expected-stop-id "1"
              stops (get-stops-by-route "100")]
          (is (= expected-stop-count (count stops)))
          (is (= expected-stop-id ((first stops) :stopid)))))
      (testing "by timepoint"
        (let [expected-stop-count 1
              expected-stop-id "2"
              stops (get-stops-by-timepoint "Timepoint 2")]
          (is (= expected-stop-count (count stops)))
          (is (= expected-stop-id ((first stops) :stopid))))))))
