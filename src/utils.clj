(ns utils
  (:require [babashka.curl :as curl]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn get-input
  "Return the input for `year` and `day`."
  [{:keys [year day]}]
  (if-let [resource (io/resource (format "%d/day%02d.txt" year day))]
    (slurp resource)
    (let [url (format "https://adventofcode.com/%d/day/%d/input" year day)
          session (-> ".secrets.edn" slurp edn/read-string :session)
          headers {:headers {"Cookie" (str "session=" session)}}
          resource (:body (curl/get url headers))]
      (spit (format "resources/%d/day%02d.txt" year day) resource)
      resource)))

(defn read-lines
  "Return a seq of seqs, partitioned by `:partition-re` and each partition
  is split by `?items-re`.
  Be default partition by newline and split by whitespace.
  E.g. the input:
  -----
  A B
  C D
  -----
  => ((\"A\" \"B\") (\"C\" \"D\"))

  When the `:as-long?`, `:as-double?` or `:as-keyword?` option is set to `true`,
  parse each element as `long`, `double` or `keyword`."
  [input & {:keys [partition-re items-re as-long? as-double? as-keyword?]
            :or {partition-re #"\n"
                 items-re #"\s+"
                 as-long? false
                 as-double? false
                 as-keyword? false}}]
  (cond-> (str/split input partition-re)
    items-re (cond->>
                 true (map #(str/split % items-re))
                 as-long? (map #(mapv parse-long %))
                 as-double? (map #(mapv parse-double %))
                 as-keyword? (map #(mapv keyword %)))

    (not items-re) (cond->>
                       as-long? (mapv parse-long)
                       as-double? (mapv parse-double)
                       as-keyword? (mapv keyword))))

(defn read-blocks
  "Return a seq of seqs partitioned by an empty newline.
  E.g. the input:
  -----
  foo
  bar

  bla
  -----
  => ((\"foo\" \"bar\") (\"bla\"))

  Options are the same as in `read-lines`."
  [input & opts]
  (read-lines input (into {:partition-re #"\n\n" :items-re #"\n"} opts)))

(defn transpose [m]
  (apply mapv vector m))

(defn queue [& args]
  (into clojure.lang.PersistentQueue/EMPTY args))
