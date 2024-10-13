<script context="module">
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import TitlePage from "$lib/templates/TitlePage.svelte";
</script>

<TitlePage>
	<span slot="title"   >One Billion Row Challenge</span>
	<span slot="subtitle">NawaMan's Java <a href="https://github.com/NawaMan/OneBRC" target="_blank">Solution</a></span>
</TitlePage>
<NavigationBar
	nextLink="./overview.html"
/>

<style>
</style>