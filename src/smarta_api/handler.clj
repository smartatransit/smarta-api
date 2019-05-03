(ns smarta-api.handler
  (:require [smarta-api.schedules.core :as schedule-client]
            [smarta-api.schedules.static.core :as static-client]
            [smarta-api.schedules.common.distance :as distance]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [ring.util.http-response :refer :all]
            [ring.middleware.json :as middleware]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults site-defaults]]))

(s/defschema Lines
  {:lines [s/Str]})

(s/defschema Stations
  {:stations [s/Str]})

(s/defschema StationsByLocation
  {:stations [{:station-name s/Str
               :location s/Str
               :distance s/Num}]})

(s/defschema Directions
  {:directions [s/Str]})

(defroutes app-routes
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "SMARTA"
                   :description "SMARTA - A lightly-enhanced API proxy for real-time MARTA data."}
            :tags [{:name "api", :description "SMARTA API"}]}}}

   (context "/api" []
     :tags ["api"]
     (context "/live" []
       :tags ["live"]
       (GET "/schedule/line/:line" [line]
         (ok (schedule-client/get-schedule-by-line line))))
     (context "/static" []
       :tags ["static"]
       (GET "/schedule/station" []
         :query-params [schedule :- String
                        station-name :- String]
         (ok (static-client/get-schedule-by-station (keyword schedule) station-name)))
       (GET "/lines" []
         :return Lines
         (ok {:lines (static-client/get-lines)}))
       (GET "/directions" []
         :return Directions
         (ok {:directions (static-client/get-directions)}))
       (GET "/stations" []
         :return Stations
         :query-params [schedule :- String
                        line :- String
                        direction :- String]
         (ok {:stations (static-client/get-stations (keyword schedule) (keyword line) (keyword direction))}))
       (GET "/stations/location" []
         :return StationsByLocation
         :query-params [latitude :- Double
                        longitude :- Double]
         (ok {:stations (distance/get-stations-by-distance latitude longitude)}))))))

(def app (-> app-routes
             (wrap-cors :access-control-allow-origin #".+"
                        :access-control-allow-methods [:get :put :post :delete])
             (wrap-defaults api-defaults)
             (middleware/wrap-json-body {:keywords? true})
             middleware/wrap-json-response))
