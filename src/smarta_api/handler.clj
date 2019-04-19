(ns smarta-api.handler
  (:require [smarta-api.schedule-client.core :as schedule-client]
            [smarta-api.schedule-client.static.core :as static-client]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
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
                     (GET "/schedule/line" []
                          :query-params [line :- String]
                          (ok (schedule-client/get-schedule-by-line line))))
            (context "/static" []
                     :tags ["static"]
                     (GET "/lines" []
                          (ok (static-client/get-lines)))
                     (GET "/directions" []
                          (ok (static-client/get-directions)))
                     (GET "/stations" [schedule line direction]
                          (ok (static-client/get-stations schedule line direction)))))))

(def app (-> app-routes
             (wrap-defaults api-defaults)))
