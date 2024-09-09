<script>
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import TitlePage from "$lib/templates/TitlePage.svelte";
</script>

<style>
</style>

<TitlePage>
	<span slot="title"      >One Billion Row Challenge</span>
	<span slot="subtitle"   >NawaMan's Java Solution</span>
	<span slot="subsubtitle">(2-3 seconds)</span>
</TitlePage>
<NavigationBar
	nextLink="./overview.html"
/>