(ns smarta-api.schedules.common.api-test
  (:require [smarta-api.schedules.common.api :refer :all])
  (:use clojure.test))

(deftest endpoint-operations
  (testing "parsed endpoints"
    (let [expected-rail-arrivals-endpoint "http://developer.itsmarta.com/RealtimeTrain/RestServiceNextTrain/GetRealtimeArrivals?apiKey=some-api-key"
          expected-bus-list-endpoint "http://developer.itsmarta.com/BRDRestService/RestBusRealTimeService/GetAllBus?apiKey=some-api-key"
          expected-bus-arrivals-endpoint "http://developer.itsmarta.com/BRDRestService/RestBusRealTimeService/GetBusByRoute/1?apiKey=some-api-key"
          rail-arrivals-endpoint (get-rail-arrivals-endpoint)
          bus-list-endpoint (get-bus-list-endpoint)
          bus-arrivals-endpoint (get-bus-arrivals-endpoint "1")]
      (is (= expected-rail-arrivals-endpoint rail-arrivals-endpoint))
      (is (= expected-bus-list-endpoint bus-list-endpoint))
      (is (= expected-bus-arrivals-endpoint bus-arrivals-endpoint)))))
