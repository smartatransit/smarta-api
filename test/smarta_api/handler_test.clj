(ns smarta-api.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [smarta-api.handler :refer :all]
            [cheshire.core :refer :all]))

(use '[ring.middleware.json :only [wrap-json-body]])

(def json-response-headers {"Content-Type" "application/json; charset=utf-8"})

(def schedule-by-line-response  {:arrivals '({:station "Test Station 1" :line  "RED"}
                                             {:station "Test Station 2" :line "GOLD"})})

(defn get-schedule-by-line-mock [line]
  schedule-by-line-response)

(def schedule-by-station-response
  {:station-name "North Springs Station"
   :red {:northbound ["5:38 AM"]}})

(defn get-schedule-by-station-mock [schedule line]
  schedule-by-station-response)

(def lines-response
  {:lines ["red" "gold" "blue" "green"]})

(defn get-lines-mock []
  (:lines lines-response))

(def directions-response
  {:directions ["northbound" "southbound" "eastbound" "westbound"]})

(defn get-directions-mock []
  (:directions directions-response))

(def stations-response
  {:stations ["Test Station"]})

(defn get-stations-mock [schedule line direction]
  (:stations stations-response))

(def stations-distance-response {:stations [{:station-name "Test Station" :location "dnh02jk56z8d" :distance 0.006395256265865522}]})

(defn get-stations-by-distance-mock [latitude longitude]
  (:stations stations-distance-response))

(defn validate-api-response [expected-status expected-headers expected-body response]
  (is (= expected-status (response :status)))
  (is (= expected-headers (response :headers)))
  (is (= expected-body (parse-string (slurp (response :body)) true))))

(deftest schedule-api
  (with-redefs [smarta-api.schedules.live.rail.core/get-rail-schedule-by-line get-schedule-by-line-mock
                smarta-api.schedules.static.core/get-schedule-by-station get-schedule-by-station-mock
                smarta-api.schedules.static.core/get-lines get-lines-mock
                smarta-api.schedules.static.core/get-directions get-directions-mock
                smarta-api.schedules.static.core/get-stations get-stations-mock
                smarta-api.schedules.common.distance/get-stations-by-distance get-stations-by-distance-mock]
    (testing "/live"
      (testing "/schedule/line/:line"
       (let [handler (-> app-routes (wrap-json-body {:keywords? true :bigdecimals? true}))
             request (-> (mock/request :get "/api/live/schedule/line/red")
                         (mock/content-type "application/json"))
             response (handler request)]
         (validate-api-response 200 json-response-headers schedule-by-line-response response))))
    (testing "/static"
      (testing "/schedule/station"
       (let [handler (-> app-routes (wrap-json-body {:keywords? true :bigdecimals? true}))
             request (-> (mock/request :get "/api/static/schedule/station?schedule=weekday&station-name=teststation")
                         (mock/content-type "application/json"))
             response (handler request)]
         (validate-api-response 200 json-response-headers schedule-by-station-response response)))
      (testing "/lines"
        (let [handler (-> app-routes (wrap-json-body {:keywords? true :bigdecimals? true}))
              request (-> (mock/request :get "/api/static/lines")
                          (mock/content-type "application/json"))
              response (handler request)]
          (validate-api-response 200 json-response-headers lines-response response)))
      (testing "/directions"
        (let [handler (-> app-routes (wrap-json-body {:keywords? true :bigdecimals? true}))
              request (-> (mock/request :get "/api/static/directions")
                          (mock/content-type "application/json"))
              response (handler request)]
          (validate-api-response 200 json-response-headers directions-response response)))
      (testing "/stations"
        (let [handler (-> app-routes (wrap-json-body {:keywords? true :bigdecimals? true}))
              request (-> (mock/request :get "/api/static/stations?schedule=weekday&line=gold&direction=northbound")
                          (mock/content-type "application/json"))
              response (handler request)]
          (validate-api-response 200 json-response-headers stations-response response)))
      (testing "/stations/location"
        (let [handler (-> app-routes (wrap-json-body {:keywords? true :bigdecimals? true}))
              request (-> (mock/request :get "/api/static/stations/location?latitude=33.823366&longitude=-84.369454")
                          (mock/content-type "application/json"))
              response (handler request)]
          (validate-api-response 200 json-response-headers stations-distance-response response))))))
