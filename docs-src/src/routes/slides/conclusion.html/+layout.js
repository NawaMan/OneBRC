// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "<p>"
                + "There are more tricks and experimentation done to achive this "
                + "... but I did not do a good job in documenting them. "
            + "</p>"
            + "<ol>"
                + "<li>So we talk about the One Billion Row Challenge.</li>"
                + "<li>... and that my solution give quite a good result </li>"
                + "<li>... while being comprehensible."
                + "<ol>"
                    + "<li>Virtual-threads are used to process data in parallel.</li>"
                    + "<li>Memory-Mapping let the OS helps us with reading data to memory.</li>"
                    + "<li>We delay the conversion to string until they really needed.</li>"
                    + "<li>Before then, byte array is used.</li>"
                    + "<li>The temperature is read and stored as an 'int'.</li>"
                    + "<li>We use the new vectorization support to speed up station name compare.</li>"
                    + "<li>We speed up the compare even more by normalize the name -- give them IDs.</li>"
                + "</ol>"
                + "</li>"
            + "</ol>"
            + "<p>That is it. Thank you for watching.</p>"
            + "<p>Once again, I am NawaMan. You can find more about me here.</p>"
            + "<p>... and my solution code can be found here.</p>"
            + "<p>Until we meet again, Bye bye for now.</p>"
            ;
    return { noteContent };
};
