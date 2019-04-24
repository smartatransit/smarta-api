(ns smarta-api.schedules.core
  (:require [smarta-api.schedules.live.rail.core :as rail-live]))

(defn get-schedule [station]
  {:arrivals (rail-live/get-rail-schedule-by-station station)})

(defn get-schedule-by-line [line]
  {:arrivals (rail-live/get-rail-schedule-by-line line)})
