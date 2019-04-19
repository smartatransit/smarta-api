(ns smarta-api.handler
  (:require [compojure.api.sweet :refer :all]
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
            (GET "/hello" []
                 :query-params [name :- String]
                 (ok {:message (str "Hello, " name)})))))

(def app (-> app-routes
             (wrap-defaults api-defaults)))
