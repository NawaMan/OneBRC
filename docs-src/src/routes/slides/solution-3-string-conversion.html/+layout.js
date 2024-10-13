// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "We touch a little bit about this trick in the previous slide. "
            + "Creating a string is expensive as it need to an isolated array of bytes to store its characters."
            + "Let's review the journey of the station name which is the string used for the whole process.";
    return { noteContent };
};
