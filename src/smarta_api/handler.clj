(ns smarta-api.handler
  (:require [smarta-api.schedules.core :as schedule-client]
            [smarta-api.schedules.static.core :as static-client]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [ring.util.http-response :refer :all]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults site-defaults]]))

(s/defschema Lines
  {:lines [s/Str]})

(s/defschema Stations
  {:stations [s/Str]})

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
       (GET "/schedule/line" []
         :query-params [line :- String]
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
         :return Directions
         :query-params [schedule :- String
                        line :- String
                        direction :- String]
         (ok {:stations (static-client/get-stations (keyword schedule) (keyword line) (keyword direction))}))))))

(def app (-> app-routes
             (wrap-defaults api-defaults)
             (middleware/wrap-json-body {:keywords? true})
              middleware/wrap-json-response))
