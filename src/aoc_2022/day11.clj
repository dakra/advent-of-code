(ns aoc-2022.day11
  "🎄 Advent of Code 2022 - Day 11"
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [utils :refer [read-blocks get-input]]))

(def demo-input* "Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1")
(def demo-input (read-blocks demo-input*))

(def input* (get-input {:year 2022 :day 11}))
(def input (read-blocks input*))

(defn trim [l]
  (str/replace l #"(^.*: |\s*)" ""))

(defn parse-input [input]
  (for [[_name starting operation test test-t test-f] input
        :let [ops (-> operation trim (subs 7))
              op  (-> ops (subs 0 1) edn/read-string)
              op-val (-> ops (subs 1) edn/read-string)]]
    {:items (-> starting trim (str/split #",") (->> (map parse-long)))
     :op op
     :op-val op-val
     :test (->> test (re-find #"\d+") parse-long)
     :true (->> test-t (re-find #"\d+") parse-long)
     :false (->> test-f (re-find #"\d+") parse-long)
     :count 0}))

(parse-input demo-input)
'({:items (79 98)       :op * :op-val 19   :test 23, :true 2, :false 3 :count 0}
  {:items (54 65 75 74) :op + :op-val 6    :test 19, :true 2, :false 0 :count 0}
  {:items (79 60 97)    :op * :op-val old  :test 13, :true 1, :false 3 :count 0}
  {:items (74)          :op + :op-val 3    :test 17, :true 0, :false 1 :count 0})

;; Part 1


(defn turn [monkeys m]
  ;; FIXME update monkeys for each item and increase :count
  )

(defn round [monkeys]
  (reduce turn monkeys monkeys))

(defn part1 [input]
  (for [_ (range 20)]
    (round input)))

(part1 demo-input)

(part1 input)


;; Part 2

(defn part2 [input]
  input)

(part2 demo-input)

(part2 input)
