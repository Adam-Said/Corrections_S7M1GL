; TP1 LISP
; Jérémie Bentolila

; exécution : 
; clisp JB-TP5.lisp 

(format t "~%1. Lambda expressions :~%")

(print ((lambda (x) (+ (* 2 x) 3)) 4))
(print ((lambda (x y) (* (+ x 2) (+ y 6))) 7 8))
(print ((lambda (v) ((lambda (x) (* 3 x)) (+ v 2))) 8))
(print ((lambda (v) ((lambda (x) (* v x)) (+ v 2))) 8))
(print ((lambda (v) ((lambda (v) (* 3 v)) (+ v 2))) 8))
(print ((lambda (x y z) (+ (* x y) (* y z))) 2 3 4))
(print ((lambda (x y) (* x x y y)) 4 5))
(print ((lambda (x) (* x x 2)) 4))
(print ((lambda (x) (* x x 2)) 50))

(defun f (x) (+ 3 x))
(defun g (v) (* 5 (f (+ v 2))))

(format t "~%g(8) : ")
(princ (g 8))

; (defun f (x) (+ v x))
; (print (f 8))

(format t "~%~%2. Fonctions globales :~%")
(defun fact (n) 
    (if (= n 0) 
        1
    (* n (fact (- n 1)))))

(defun fibo (n)
    (if (= n 0)
        0
    (if (= n 1)
        1
    (+ (fibo (- n 1)) (fibo (- n 2))))))


(format t "~%fact(6) : ")
(princ (fact 6))

(format t "~%fibo(15) : ")
(princ (fibo 15))
; fibo(50) : très long
; (format t "~%fibo(50) : ")
; (princ (fibo 50))

(format t "~%~%3. Listes et cellules :~%")

(print (cdr '((()))))
(print (cdr '(())))
(print (cdr '()))

(print (car '((()))))
(print (car '(())))
(print (car '()))

; faux : car ((())) et (()) ne sont pas le même objet
(print (eq (car '((()))) '(()) ))
; vrai : car ((())) et (()) sont structurellement identiques
(print (equal (car '((()))) '(()) ))

(defun invList (l)
    (if (equal l nil)
        '()
    (append (invList (cdr l)) (list (car l))))
)

(format t "~%invList (1 2 3 4) : ")
(princ (invList '(1 2 3 4)))

(format t "~%invList (1 (2 3) 4) : ")
(princ (invList '(1 (2 3) 4)))

(format t "~%invList (1 (2) (3) 4) : ")
(princ (invList '(1 (2) (3) 4)))

(format t "~%~%4. Fonctions sur les listes plates :~%")

(defun myMember (x l)
    (if (equal l nil)
        '()
    (if (= (car l) x)
        l
    (myMember x (cdr l))))
)

(defun myLength (l)
    (if (equal l nil)
        0
    (+ (myLength (cdr l)) 1))
)

(defun myLast (l)
    (if (equal (cdr l) nil)
        (car l)
    (myLast (cdr l)))
)

(defun makelist_decr (n)
    (if (= n 0)
        nil
    (cons n (makelist_decr (- n 1))))
)

(defun makelist_incr (n)
    (if (= n 0)
        nil
    (append (makelist_incr (- n 1)) (list n)))
)

(defun copyList (l)
    (if (equal l nil)
        nil
    (if (integerp (car L))
        (append (list (car l)) (copyList (cdr l)) )
        (copyList (cdr l))
    ))
)

(defun myRemove (x l)
    (if (equal l nil)
        nil
    (if (equal (car l) x)
        (myRemove x (cdr l))
    (append (list (car l)) (myRemove x (cdr l)))
    ))
)

(defun myRemove-first (x l)
    (if (equal l nil)
        nil
    (if (equal (car l) x)
        (cdr l)
    (append (list (car l)) (myRemove-first x (cdr l)))
    ))
)

(defun myAppend (l1 l2)
    (if (equal l1 nil)
        l2
        (cons (car l1) (myAppend (cdr l1) l2))
    )
)

(defun myAdjoin (x l)
    (if (equal l nil)
        (cons x l)
    (if (equal (car l) x)
        l
    (cons (car l) (myAdjoin x (cdr l)))
    ))
)

(format t "~%myMember(3 '(1 2 3 4)) : ")
(princ (myMember 3 '(1 2 3 4)))

(format t "~%myLength('(1 2 3 4)) : ")
(princ (myLength '(1 2 3 4)))

(format t "~%myLast('(1 2 3 4)) : ")
(princ (myLast '(1 2 3 4)))

(format t "~%makelist_decr(4) : ")
(princ (makelist_decr 4))

(format t "~%makelist_incr(4) : ")
(princ (makelist_incr 4))

(format t "~%copyList('(1 2 3 (6 7) 4)) : ")
(princ (copyList '(1 2 3 (6 7) 4)))

(format t "~%myRemove('(6 7) '(1 2 (6 7) 3 (6 7) 4)) : ")
(princ (myRemove '(6 7) '(1 2 (6 7) 3 (6 7) 4)))

(format t "~%myRemove-first('(6 7) '(1 2 (6 7) 3 (6 7) 4)) : ")
(princ (myRemove-first '(6 7) '(1 2 (6 7) 3 (6 7) 4)))

(format t "~%myAppend('(1 2 3 4) '(5 6 7)) : ")
(princ (myAppend '(1 2 3 4) '(5 6 7)))

(format t "~%myAdjoin(5 '(1 2 3 4)) : ")
(princ (myAdjoin 5 '(1 2 3 4)))

(format t "~%myAdjoin(3 '(1 2 3 4)) : ")
(princ (myAdjoin 3 '(1 2 3 4)))


(format t "~%~%5. Fonctions sur les arbres binaires:~%")

; les arbres, des listes avec : 
; (car tree) : filsG de l'arbre
; (cdr tree) : filsD de l'arbre

(defun treeSize (tree)
    (if (equal tree nil)
        0
        (if (integerp (car tree))
            (+ 1 (treeSize (cdr tree)))
            (+ (treeSize (car tree)) (treeSize (cdr tree)))
        )
    )
)

(defun copyTree (tree)
    (if (equal tree nil)
        nil
        (if (integerp (car tree))
            (cons (car tree) (copyTree (cdr tree)))
            (cons (copyTree (car tree)) (copyTree (cdr tree)))
        )
    )
)

(defun mySubst (x y tree)
    (if (equal tree nil)
        nil
        (if (integerp (car tree))
            (if (= x (car tree))
                (cons y (mySubst x y (cdr tree)))
                (cons (car tree) (mySubst x y (cdr tree)))
            )
            (cons (mySubst x y (car tree)) (mySubst x y (cdr tree)))
        )
    )
)

(defun treeLeaves (tree)
    (if (equal tree nil)
        nil
        (if (integerp (car tree))
            (cons (car tree) (treeLeaves (cdr tree)))
            (append (treeLeaves (car tree)) (treeLeaves (cdr tree)))
        )
    )
)

(format t "~%treeSize('((1 (2 (5 (6)))) (3 (4 (7)))))) : ")
(princ (treeSize '((1 (2 (5 (6)))) (3 (4 (7))))))

(format t "~%copyTree('((1 (2 (5 (6)))) (3 (4 (7)))))) : ")
(princ (copyTree '((1 (2 (5 (6)))) (3 (4 (7))))))

(format t "~%mySubst(2 8 '((1 (2 (5 (6)))) (2 (2 (7)))))) : ")
(princ (mySubst 2 8 '((1 (2 (5 (6)))) (2 (2 (7))))))

(format t "~%treeLeaves('((1 (2 (5 (6)))) (2 (2 (7)))))) : ")
(princ (treeLeaves '((1 (2 (5 (6)))) (2 (2 (7))))))
