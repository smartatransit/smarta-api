(ns smarta-api.handler
  (:require [smarta-api.schedules.live.rail.core :as rail-live-client]
            [smarta-api.schedules.static.core :as static-client]
            [smarta-api.schedules.common.distance :as distance]
            [smarta-api.schemas.rail :as rail-schema]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [ring.middleware.json :as middleware]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults site-defaults]]))

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
         (ok (rail-live-client/get-rail-schedule-by-line line)))
       (GET "/schedule/station" []
            :query-params [station-name :- String]
            (ok (rail-live-client/get-rail-schedule-by-station station-name))))
     (context "/static" []
       :tags ["static"]
       (GET "/schedule/station" []
         :query-params [schedule :- String
                        station-name :- String]
         (ok (static-client/get-schedule-by-station (keyword schedule) station-name)))
       (GET "/lines" []
         :return rail-schema/Lines
         (ok {:lines (static-client/get-lines)}))
       (GET "/directions" []
         :return rail-schema/Directions
         (ok {:directions (static-client/get-directions)}))
       (GET "/stations" []
         :return rail-schema/Stations
         :query-params [schedule :- String
                        line :- String
                        direction :- String]
         (ok {:stations (static-client/get-stations (keyword schedule) (keyword line) (keyword direction))}))
       (GET "/stations/location" []
         :return rail-schema/StationsByLocation
         :query-params [latitude :- Double
                        longitude :- Double]
         (ok {:stations (distance/get-stations-by-distance latitude longitude)}))))))

(def app (-> app-routes
             (wrap-cors :access-control-allow-origin #".+"
                        :access-control-allow-methods [:get :put :post :delete])
             (wrap-defaults api-defaults)
             (middleware/wrap-json-body {:keywords? true})
             middleware/wrap-json-response))
