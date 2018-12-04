(ns misc.obdownloader)

;; todo multithreading

(def url "http://...") ;;ob url

(defn copy [uri file]
  (with-open [in (io/input-stream uri)
              out (io/output-stream file)]
    (io/copy in out)))

(doseq [file-name (for [n (range 0 10)
                        extension ["jpg" "png" "gif" "jpeg"]]
                    (str (format "%05d" n) "." extension) )]
  (let [uri (str url file-name)]
    (try
      (copy uri file-name)
      (catch Exception e (str "can't download " (.getMessage e)))))) ;; todo write exception to stdout
