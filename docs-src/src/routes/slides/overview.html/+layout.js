// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "The One Billion Row Challenge proposed by Mr. Morling on Jan 1st 2024. "
            + "To write a program that can read a CSV-liked file with one billion rows."
            + "Seeing the twitter post at the time, I thought this was an I/O bound problem."
            + "So, I didn't pay attention to it.";
    return { noteContent };
};
