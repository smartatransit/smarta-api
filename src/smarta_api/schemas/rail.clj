(ns smarta-api.schemas.rail
  (:require [schema.core :as s :include-macros true]))

;Old schemas to be replaced
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

;New schemas
(s/defschema RailLines (s/enum "Red" "Gold" "Green" "Blue"))

(s/defschema RailDirections (s/enum "Northbound" "Southbound" "Eastbound" "Westbound"))

(s/defschema StationDetail
  {:name s/Str
   :line RailLines
   :direction RailDirections})

(s/defschema TrainDetail
  {:train-id s/Str
   :next-station s/Str
   :destination s/Str
   :next-arrival s/Str
   :waiting-seconds s/Str
   :waiting-time s/Str
   :event-time s/Str})
