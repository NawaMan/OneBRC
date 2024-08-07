<script>
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import PageTitle from "$lib/components/PageTitle.svelte";
    let value = "";
</script>

<style>
</style>

<div class="content">
	<NavigationBar
		firstLink="/"
		lastLink="/"
	/>
	<PageTitle>
		<span slot="title"      >One Billion Row Challenge</span>
		<span slot="subtitle"   >NawaMan's Java Solution</span>
		<span slot="subsubtitle">(2-3 Seconds Without Using Unsafe or Graal VM)</span>
	</PageTitle>
</div>