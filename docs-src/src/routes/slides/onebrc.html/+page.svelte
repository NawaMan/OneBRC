<script context="module">
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import Note from "$lib/components/Note.svelte";
	import TitlePage from "$lib/templates/TitlePage.svelte";
</script>

<TitlePage>
	<span slot="title"   >One Billion Row Challenge</span>
	<span slot="subtitle">NawaMan's Java <a href="https://github.com/NawaMan/OneBRC" target="_blank">Solution</a></span>
</TitlePage>
<NavigationBar
	nextLink="./overview.html"
/>
<Note>
	Hi! I am NawaMan.
	Let's talk about my "One Billion Row Challenge" solution.
</Note>

<style>
</style>