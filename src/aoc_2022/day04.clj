(ns aoc-2022.day04
  "🎄 Advent of Code 2022 - Day 4"
  (:require [clojure.set :as set]
            [clojure.string :as str]
            [utils :refer [read-lines get-input]]))

(defn sections->set [sections]
  (let [[start end] (str/split sections #"-")]
    (set (range (parse-long start) (inc (parse-long end))))))

(defn parse-input [input]
  (->> (read-lines input {:items-re #","})
       (map #(map sections->set %))))

(def demo-input* "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")
(def demo-input (parse-input demo-input*))
(def input (parse-input (get-input {:year 2022 :day 4})))

;; Part 1

(defn part1 [input]
  (->> input
       (filter #(or (apply set/subset? %) (apply set/superset? %)))
       count))

(part1 demo-input)
;; => 2

(part1 input)
;; => 530


;; Part 2

(defn part2 [input]
  (->> input
       (filter #(seq (apply set/intersection %)))
       count))

(part2 demo-input)
;; => 4

(part2 input)
;; => 903
