%%Interface contract: Returns the K-th lexicographical number from 1 to N.
%%Because why not count like dictionaries instead of normal humans? Idk why cause I am a normal person
-spec find_kth_number(pos_integer(), pos_integer()) -> pos_integer().
find_kth_number(N, K) ->
    %% Curr starts at 1 because 0 is just... not lexicographically relevant
    %% Also K-1 because we are already on 1 so its whtv
    Curr = 1,
    loop(N, Curr, K - 1).

%%Recursive DFS-inspired traversal:
%%Either we go *deeper* (child nodes) or *wider* (next sibling prefix)
%%K is decremented as we consume positions in the lexicographical order
loop(_N, Curr, 0) ->
    %% Base case: when K reaches 0 boom! We found our number
    Curr;
loop(N, Curr, K) ->
    %%Compute how many numbers start with the current prefix
    %%Because we are skipping entire subtrees like it's brunch :)
    Steps = count_steps(N, Curr, Curr + 1),
    case Steps =< K of
        true ->
            %%Not our subtree so skip the whole thing and subtract its weight
            loop(N, Curr + 1, K - Steps);
        false ->
            %%It's in this subtree then go deeper and reduce K by 1 (we visited Curr)
            loop(N, Curr * 10, K - 1)
    end.

%%Counts how many numbers exist between Curr and Next within [1, N]
%%This is where the actual nerd magic happens: quantifying an entire prefix group
-spec count_steps(pos_integer(), pos_integer(), pos_integer()) -> non_neg_integer().
count_steps(N, Curr, Next) ->
    count_steps(N, Curr, Next, 0).

count_steps(N, Curr, Next, Acc) when Curr =< N ->
    %% min(N + 1, Next) ensures we don’t overshoot N’s lex ceiling
    %%We are literally counting nodes in a digital trie here. Wild
    NewAcc = Acc + min(N + 1, Next) - Curr,
    %%Then go one level deeper by multiplying by 10 like adding digits
    count_steps(N, Curr * 10, Next * 10, NewAcc);
count_steps(_N, _Curr, _Next, Acc) ->
    %%Weve gone past N time to return our glorious accumulated count
    Acc.

%%Why reimplement min()? Because Erlang doesn't hand it to us, thats why. Erlang is a btc for real 
min(A, B) when A =< B -> A;
min(_A, B) -> B.
