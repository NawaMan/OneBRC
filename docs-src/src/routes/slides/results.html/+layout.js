// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "<p>The result?</p>"
            + "<p>Pretty good, actually.</p>"
            + "<p>The program run in between 1.8 seconds to 3 seconds on these two machines. "
            + "You can click them to see more details.</p>"
            + "<p>"
            + "The code achives this without using 'unsafe' and is not using 'GraalVM'. "
            + "It is very comprehensible -- well partitioned and don't have hard to read bitwise operations."
            + "</p>"
            + "<p>"
            + "The code is just 450 lines."
            + "You can see the whole code on GitHub."
            + "</p>";
    return { noteContent };
};
