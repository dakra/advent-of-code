(ns aoc-2022.day02
  "🎄 Advent of Code 2022 - Day 2"
  (:require [utils :refer [read-lines get-input]]))

(def demo-input* "A Y
B X
C Z")
(def demo-input (read-lines demo-input* {:as-keyword? true}))

(def input* (get-input {:year 2022 :day 2}))
(def input (read-lines input* {:as-keyword? true}))

;; Part 1

(def opp->play
  {:A :rock
   :B :paper
   :C :scissors})

(def play->points
  {:rock     1
   :paper    2
   :scissors 3})

(def winning-points
  {:rock     {:rock     3
              :paper    0
              :scissors 6}
   :paper    {:rock     6
              :paper    3
              :scissors 0}
   :scissors {:rock     0
              :paper    6
              :scissors 3}})

(def you->play
  {:X :rock
   :Y :paper
   :Z :scissors})

(defn ->points [[opp you]]
  (let [o (opp->play opp)
        y (you->play you)
        win-points (get-in winning-points [y o])
        play-points (play->points y)]
    (+ play-points win-points)))

(->> demo-input
     (map ->points)
     (apply +))
;; => 15

(->> input
     (map ->points)
     (apply +))
;; => 11063

;; Part 2

(def you->outcome
  {:X :lose
   :Y :draw
   :Z :win})

(def outcome->play
  {:win  {:rock     :paper
          :paper    :scissors
          :scissors :rock}
   :draw {:rock     :rock
          :paper    :paper
          :scissors :scissors}
   :lose {:rock     :scissors
          :paper    :rock
          :scissors :paper}})

(defn ->points2 [[opp you]]
  (let [o (opp->play opp)
        outcome (you->outcome you)
        play (get-in outcome->play [outcome o])
        win-points (get-in winning-points [play o])
        play-points (play->points play)]
    (+ play-points win-points)))

(->> demo-input
     (map ->points2)
     (apply +))
;; => 12

(->> input
     (map ->points2)
     (apply +))
;; => 10349
