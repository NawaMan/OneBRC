// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
        = "<p>Before talking about the solution, let review the challange in more details.</p>"
        + "<p>whcih is ...</p>";
    return { noteContent };
};
