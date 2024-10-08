// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent = "Hi I am NawaMan. Let's talk about my One Billion Row Challenge solution.";
    return { noteContent };
};