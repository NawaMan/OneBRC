// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "The One Billion Row Challenge was proposed by Gunnar Morling on Jan 1st of 2024. "
            + "The challenge is to write a program that can read a CSV-liked file with one billion rows of data. "
            + "I saw the original twitter post on that day but I thought this was I/O bound which I am not interested in. "
            + "So, I didn't pay attention to it.";
    return { noteContent };
};
