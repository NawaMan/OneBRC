// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "<p>However, some time in August, I saw these two YouTube VDOs which sparked my interest.</p>"
            + "<p>"
                + "I then, learned that the benchmark will be run on a machine with large memory ... "
                + "which changes the problem to be more CPU bound."
            + "</p>"
            + "<p>"
                + "Dispite thinking that the clever Mr. Morling might just want someone to solve the problem for him ... "
                + "I decided to give it a try."
            + "</p>"
            + "<p>"
                + "The main work took about 1-2 weeks but more time spent of setting up benchmark on AWS "
                + "and making these slides which I had to learn Svelte first to make them -- talk about digression."
            + "<p>";
    return { noteContent };
};
