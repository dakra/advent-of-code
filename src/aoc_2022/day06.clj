(ns aoc-2022.day06
  "🎄 Advent of Code 2022 - Day 6"
  (:require [utils :refer [get-input]]))

(def demo-input-1 "mjqjpqmgbljsphdztnvjfqwrcgsmlb") ;; 7
(def demo-input-2 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") ;; 11

(def input (get-input {:year 2022 :day 6}))

(defn solve [input partition-size]
  (->> input
       (partition partition-size 1)
       (take-while #(apply (complement distinct?) %))
       count
       (+ partition-size)))

;; Part 1

(solve demo-input-1 4) ;; => 7
(solve demo-input-2 4) ;; => 11
(solve input 4) ;; => 1578

;; Part 2

(solve demo-input-1 14) ;; => 19
(solve demo-input-2 14) ;; => 26
(solve input 14) ;; => 2178
