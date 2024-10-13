// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "<p>The \"One Billion Row Challenge\" was proposed by Gunnar Morling on Jan 1st of 2024.</p>"
            + "<p>The challenge is to write a program that can read a CSV-liked file with one billion rows of data.</p>"
            + "<p>"
                + "I saw the original twitter post on that day but I thought this was I/O bound problem which I am not interested in. "
                + "and did not pay attention to it."
            + "</p>";
    return { noteContent };
};
