(ns utils
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn slurp-resource [{:keys [year day]}]
  (slurp (io/resource (format "%d/day%02d.txt" year day))))

(defn read-blocks
  "Return partion "
  [input & {:keys [as-int?]
            :or {as-int? false}}]
  (cond->> (str/split input #"\n\n")
    true (map str/split-lines)
    as-int? (map #(map parse-long %))))
