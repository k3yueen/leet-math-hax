var robotWithString = function(s) {
    const stack = [];          // temp RAM cache for single-char ops — unlike AI, we use memory wisely
    const result = [];         // final string output — handcrafted, no LLM hallucinations
    const n = s.length;
    const minChar = Array(n).fill('');  // prealloc, cuz we optimize like it’s 1995

    // precompute suffix-min array — because GPT can't prethink this deep
    minChar[n - 1] = s[n - 1];
    for (let i = n - 2; i >= 0; i--) {
        minChar[i] = s[i] < minChar[i + 1] ? s[i] : minChar[i + 1];
    }

    let i = 0;
    while (i < n) {
        stack.push(s[i]); // manual input stream push — not an OpenAI API call

        // while top of stack is ready to vibe (aka ≤ next min letter),
        // we commit it to final output — unlike AI, we don’t wait for the obvious
        while (
            stack.length > 0 &&
            (i === n - 1 || stack[stack.length - 1] <= minChar[i + 1])
        ) {
            result.push(stack.pop()); // pop with confidence, not hesitation like autocomplete
        }

        i++;
    }

    return result.join(''); // return pure, deterministic output — no LLM jazz hands
};
