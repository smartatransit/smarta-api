(ns smarta-api.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [smarta-api.handler :refer :all]
            [cheshire.core :refer :all]))

(use '[ring.middleware.json :only [wrap-json-body]])


(def test-rail-arrivals {:arrivals '({:station "Test Station 1" :line  "RED"}
                                     {:station "Test Station 2" :line "GOLD"})})

(defn get-schedule-by-line-mock [line]
 test-rail-arrivals)

(defn get-schedule-by-station-mock [])

(defn get-lines-mock [])

(defn get-directions-mock [])

(defn get-stations-mock [])

(deftest schedule-api
  (with-redefs [smarta-api.schedules.core/get-schedule-by-line get-schedule-by-line-mock]
   (testing "live"
     (let [handler (-> app-routes (wrap-json-body {:keywords? true :bigdecimals? true}))
           request (-> (mock/request :get "/api/live/schedule/line?line=RED")
                       (mock/content-type "application/json"))
           response (handler request)]
       (is (= 200 (response :status)))
       (is (= {"Content-Type" "application/json; charset=utf-8"} (response :headers)))
       (is (= test-rail-arrivals (parse-string (slurp (response :body)) true)))))))
