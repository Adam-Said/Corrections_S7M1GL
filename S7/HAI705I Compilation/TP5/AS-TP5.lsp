; TP1 LISP
; Adam Said

(print "Ex1. lambda-expressions")
(princ ((lambda (x) (+ (* 2 x) 3)) 4))
(print ((lambda (x y) (* (+ x 2) (+ y 6))) 7 8))
(print ((lambda (v) ((lambda (x) (* 3 x)) (+ v 2))) 8))
(print ((lambda (v) ((lambda (x) (* v x)) (+ v 2))) 8))
(print ((lambda (v) ((lambda (v) (* 3 v)) (+ v 2))) 8))
(print ((lambda (x y z) (+ (* x y) (* y z))) 2 3 4))
(print ((lambda (x y) (* x x y y)) 4 5))
(print ((lambda (x) (* x x 2)) 4))
(print ((lambda (x) (* x x 2)) 4))

(print "Ex2. fonctions globales")
(defun f (x) (+ 3 x))
(defun g (v) (* 5 (f (+ v 2))))

(print (g 8))

; (defun f (x) (+ v x)) ne peut pas fonctionner car v n'est pas défini

(defun fac (n) (if (= n 0) 1 (* n (fac (- n 1)))))

(print "fac 5 = ")
(princ (fac 5))

(defun fibo (n) (if (= n 1) 1 (if (= n 0) 0 (+ (fibo (- n 1)) (fibo (- n 2))))))
(print "fibo 15 = ")
(princ (fibo 15))

(print "Ex3. les listes et les cellules")

(print (car '()))
(print (car '(())))
(print (cdr '()))
(print (eq '(()) '((()))))
(print (equal '(()) '((()))))
;(print (= '(()) '((()))))

(defun countList (l) (if (null l) 0 (+ 1 (countList (cdr l)))))
(print "countList '(1 2 3 4 5) = ")
(princ (countList '(1 2 3 4 5)))

(print "countList '(1 (2 3) 4) = ")
(princ (countList '(1 (2 3) 4)))

(print "countList '(1 (2) (3) 4) = ")
(princ (countList '(1 (2) (3) 4)))

(defun invList (l) (if (null l) '() (append (invList (cdr l)) (list (car l)))))
(print "invList '(1 2 3 4 5) = ")
(princ (invList '(1 2 3 4 5)))

(print "invList '(1 (2 3) 4) = ")
(princ (invList '(1 (2 3) 4)))

(print "invList '(1 (2) (3) 4) = ")
(princ (invList '(1 (2) (3) 4)))

(print "Ex4. fonctions sur les listes plates")

(defun member (x l) (if(eq l '()) '() (if(eq x (car l)) l (member x (cdr l)))))
(print "member 3 '(1 2 3 4 5) = ")
(princ (member 3 '(1 2 3 4 5)))

(defun length (l) (if (null l) 0 (+ 1 (countList (cdr l)))))
(print "length '(1 2 3 4 5) = ")
(princ (length '(1 2 3 4 5)))

(defun last (l) (if (null l) '() (if (null (cdr l)) (car l) (last (cdr l)))))
(print "last '(1 2 3 4 5) = ")
(princ (last '(1 2 3 4 5)))

(defun remove (x l) (if (null l) '() (if (eq x (car l)) (remove x (cdr l)) (cons (car l) (remove x (cdr l))))))
(print "remove 3 '(1 2 3 4 5) = ")
(princ (remove 3 '(1 2 3 4 5)))

(defun append (l1 l2) (if (null l1) l2 (cons (car l1) (append (cdr l1) l2))))
(print "append '(1 2 3) '(4 5 6) = ")
(princ (append '(1 2 3) '(4 5 6)))

(defun adjoin (x l) (if (member x l) l (cons x l)))
(print "adjoin 6 '(1 2 3 4 5) = ")
(princ (adjoin 6 '(1 2 3 4 5)))

(print "Ex5. fonctions sur les arbres binaires")
