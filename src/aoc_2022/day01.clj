(ns aoc-2022.day01
  "🎄 Advent of Code 2022 - Day 1"
  (:require [utils :refer [read-blocks get-input]]))

(def demo-input "1000
2000
3000

4000

5000
6000

7000
8000
9000

10000")
(def input (get-input {:year 2022 :day 1}))

(defn input->list [input]
  (->> (read-blocks input {:as-long? true})
       (map #(apply + %))
       (sort >)))

;; Part 1

(-> demo-input input->list first)
;; => 24000

(-> input input->list first)
;; => 70764

;; Part 2
(->> input input->list (take 3) (apply +))
;; => 203905
