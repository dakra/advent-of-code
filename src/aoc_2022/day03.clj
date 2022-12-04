(ns aoc-2022.day03
  "🎄 Advent of Code 2022 - Day 3"
  (:require [clojure.set :as set]
            [utils :refer [read-lines get-input]]))

(def demo-input* "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")
(def demo-input (read-lines demo-input* {:items-re nil}))

(def input* (get-input {:year 2022 :day 3}))
(def input (read-lines input* {:items-re nil}))

;; Part 1
(defn find-dups [input]
  (->> input
       (map #(split-at (/ (count %) 2) %))
       (map #(apply set/intersection (map set %)))
       (map first)))

(int \a) ;; => 97 -96 -> 1
(int \A) ;; => 65 -39 -> 27

(defn badge->prio [x]
  (let [offset (if (Character/isLowerCase x) 96 38)]
    (- (int x) offset)))

(defn part1 [input]
  (->> input
       find-dups
       (map badge->prio)
       (apply +)))

(part1 demo-input)
;; => 157

(part1 input)
;; => 8139

;; Part 2

(defn part2 [input]
  (->> input
       (partition 3)
       (map #(map set %))
       (map #(apply set/intersection %))
       (map first)
       (map badge->prio)
       (apply +)))

(part2 demo-input)
;; => 70

(part2 input)
;; => 2668
