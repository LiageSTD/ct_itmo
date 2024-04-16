prime(N) :-
    N > 3,
    N mod 2 =\= 0,
    prime_helper(N, 5).
prime(N) :-
    N =< 3,
    N > 1.
prime_helper(N, X) :-
    X >= N,
    !.
prime_helper(N, X) :-
    N mod X =\= 0,
    X1 is X + 2,
    prime_helper(N, X1).
composite(N) :- \+ prime(N).

cube_divisors(N,Arr) :-
    N1 is (N * N * N),
    prime_divisors(N1,Arr), 
    !.
	
prime_divisors(N, Arr) :-
    checkTwo(N,Arr).
checkTwo(N,[2|Arr]) :-
    N mod 2 =:= 0,
    N1 is N // 2,
    checkTwo(N1,Arr).
checkTwo(N,Arr) :-
    prime_divisors(N, 3, Arr),
    !.

prime_divisors(1, _, []).

prime_divisors(N, X, [X|Arr]) :-
    N \= 0,
    prime(X),
    N mod X =:= 0,
    N1 is (N // X),
    prime_divisors(N1, X, Arr).
 
prime_divisors(N, X, Arr) :-
    X < N + 2,
    X1 is X + 2,
    prime_divisors(N, X1, Arr).