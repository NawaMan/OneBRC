// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "We touch a little bit in the previous trick that "
            + " ... creating a string is expensive as a string needs an isolated array of bytes to store its characters."
            + "Let's review the journey of the station name which is the string used for the whole process."
            + "<br/>"
            + "..."
            + "<br/>"
            + "Therefore, we can delay the conversion of the station name to a string until we really need at the sorting step. ";
    return { noteContent };
};
