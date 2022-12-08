(ns aoc-2022.day08
  "🎄 Advent of Code 2022 - Day 8"
  (:require [hyperfiddle.rcf :as rcf]
            [utils :refer [read-lines get-input transpose]]))

(def demo-input* "30373
25512
65332
33549
35390")
(def demo-input (vec (read-lines demo-input* {:items-re #"" :as-long? true})))

(def input* (get-input {:year 2022 :day 8}))
(def input (vec (read-lines input* {:items-re #"" :as-long? true})))

;; demo-input
[[3 0 3 7 3]
 [2 5 5 1 2]
 [6 5 3 3 2]
 [3 3 5 4 9]
 [3 5 3 9 0]]

;; demo transposed:
[[3 2 6 3 3]
 [0 5 5 3 5]
 [3 5 3 5 3]
 [7 1 3 4 9]
 [3 2 2 9 0]]

;; Part 1

(defn row-visible? [row i]
  (let [tree-height (nth row i)
        [before [_ & after]] (split-at i row)]
    (not (and (some #(<= tree-height %) before)
              (some #(<= tree-height %) after)))))

(defn part1 [input]
  (let [grid-size (dec (count input))
        input-t (transpose input)]
    (->> (for [x (range (inc grid-size))
               y (range (inc grid-size))]
           (or (= x 0)
               (= y 0)
               (= x grid-size)
               (= y grid-size)
               (row-visible? (nth input y) x)
               (row-visible? (nth input-t x) y)))
         (filter identity)
         count)))

(rcf/tests
 "Part 1"
 (part1 demo-input) := 21
 (part1 input) := 1854
 )

;; Part 2

(defn row-score [row i]
  (let [tree-height (nth row i)
        [before [_ & after]] (split-at i row)]
    (* (min (count before) (inc (count (take-while #(> tree-height %) (reverse before)))))
       (min (count after) (inc (count (take-while #(> tree-height %) after)))))))

(defn part2 [input]
  (let [grid-size (dec (count input))
        input-t (transpose input)]
    (->> (for [x (range 1 grid-size)
               y (range 1 grid-size)]
           (* (row-score (nth input y) x)
              (row-score (nth input-t x) y)))
         (sort >)
         first)))

(rcf/tests
 "Part 2"
 (part2 demo-input) := 8
 (part2 input) := 527340
 )
