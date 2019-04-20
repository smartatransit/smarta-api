(ns smarta-api.schedules.static.forecast
  (:use clojure.core.matrix
        [incanter.charts :only [scatter-plot add-lines]]
        [incanter.stats :only [linear-model]]
        [incanter.core :only [view]])
  (:require [clojure.core.matrix.operators :as M]
           [clatrix.core :as cl]))

(def X (cl/matrix [4.0 4.5 5.0 5.5 6.0
                   6.5 7.0 7.5 8.0 8.5]))

(def Y (cl/vector [4.0 4.5 5.25 5.6 6.0
                   6.75 7.25 7.5 8.25 8.75]))


(def linear-samp-scatter
  (scatter-plot X Y))

(defn plot-scatter []
  (view linear-samp-scatter))

(def samp-linear-model
  (linear-model Y X))

(defn plot-model []
  (view (add-lines linear-samp-scatter X (:fitted samp-linear-model))))
