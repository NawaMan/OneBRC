// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent = "Before talking about the solutio, let review the challange in more details.";
    return { noteContent };
};
