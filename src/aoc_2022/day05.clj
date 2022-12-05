(ns aoc-2022.day05
  "🎄 Advent of Code 2022 - Day 5"
  (:require [clojure.string :as str]
            [utils :refer [read-blocks get-input transpose]]))

;; NOTE Trailing whitespace from input are needed
(def demo-input* "    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")
(def demo-input (read-blocks demo-input*))

(def input* (get-input {:year 2022 :day 5}))
(def input (read-blocks input*))

(defn input->stacks [input]
  (->> input
       first
       transpose
       (map #(apply str %))
       (map str/trim)
       (remove #(re-find #"([\[\] ]|^$)" %))
       (map str/reverse)
       (map (fn [x] [(str (first x)) (vec (rest x))]))
       (into {})))

(defn input->move [x]
  (let [[move from to] (re-seq #"\d+" x)]
    {:move (parse-long move) :from from :to to}))

(defn input->moves [input]
  (->> input
       second
       (map input->move)))

;; Part 1

(defn move [stacks {:keys [from to]}]
  (-> stacks
      (update from pop)
      (update to #(conj % (peek (get stacks from))))))

(defn part1 [input]
  (let [stacks (input->stacks input)]
    (->> input
         input->moves
         (mapcat #(repeat (:move %) %))
         (reduce move stacks)
         sort
         (map second)
         (map last)
         str/join)))

(part1 demo-input)
;; => "CMZ"

(part1 input)
;; => "TLNGFGMFN"


;; Part 2

(defn move-multi [stacks {:keys [move from to]}]
  (let [from-stack (get stacks from)
        [rem mov] (split-at (- (count from-stack) move) from-stack)]
    (-> stacks
        (assoc from (vec rem))
        (update to #(into % mov)))))

(defn part2 [input]
  (let [stacks (input->stacks input)]
    (->> input
         input->moves
         (reduce move-multi stacks)
         sort
         (map second)
         (map last)
         str/join)))

(part2 demo-input)
;; => "MCD"

(part2 input)
;; => "FGLQJCMBD"
