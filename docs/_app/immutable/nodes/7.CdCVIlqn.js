import{s as j,c as S,u as w,g as E,a as A,n as B}from"../chunks/scheduler.DJUZaEzB.js";import{S as z,i as F,e as m,s as k,c as h,h as C,d as f,k as q,n as $,a as d,l as b,t as g,b as v,o as J,p as L,q as M,r as O,g as y}from"../chunks/index.4LyIrb68.js";import{N as G}from"../chunks/NavigationBar.CycEho2D.js";import"../chunks/CtrlBtn.C1TPePq_.js";import"../chunks/scaleMode.q8eS2gCe.js";const K=l=>({}),R=l=>({}),Q=l=>({}),T=l=>({}),U=l=>({}),V=l=>({});function W(l){let t,o,e,a,n,r,u;const x=l[1].title,_=S(x,l,l[0],V),P=l[1].subtitle,p=S(P,l,l[0],T),N=l[1].subsubtitle,c=S(N,l,l[0],R);return{c(){t=m("div"),o=m("h1"),_&&_.c(),e=k(),a=m("p"),p&&p.c(),n=k(),r=m("p"),c&&c.c(),this.h()},l(s){t=h(s,"DIV",{class:!0});var i=C(t);o=h(i,"H1",{class:!0});var D=C(o);_&&_.l(D),D.forEach(f),e=q(i),a=h(i,"P",{class:!0});var H=C(a);p&&p.l(H),H.forEach(f),n=q(i),r=h(i,"P",{class:!0});var I=C(r);c&&c.l(I),I.forEach(f),i.forEach(f),this.h()},h(){$(o,"class","title svelte-1973x8p"),$(a,"class","subtitle svelte-1973x8p"),$(r,"class","subsubtitle svelte-1973x8p"),$(t,"class","page svelte-1973x8p")},m(s,i){d(s,t,i),b(t,o),_&&_.m(o,null),b(t,e),b(t,a),p&&p.m(a,null),b(t,n),b(t,r),c&&c.m(r,null),u=!0},p(s,[i]){_&&_.p&&(!u||i&1)&&w(_,x,s,s[0],u?A(x,s[0],i,U):E(s[0]),V),p&&p.p&&(!u||i&1)&&w(p,P,s,s[0],u?A(P,s[0],i,Q):E(s[0]),T),c&&c.p&&(!u||i&1)&&w(c,N,s,s[0],u?A(N,s[0],i,K):E(s[0]),R)},i(s){u||(g(_,s),g(p,s),g(c,s),u=!0)},o(s){v(_,s),v(p,s),v(c,s),u=!1},d(s){s&&f(t),_&&_.d(s),p&&p.d(s),c&&c.d(s)}}}function X(l,t,o){let{$$slots:e={},$$scope:a}=t;return l.$$set=n=>{"$$scope"in n&&o(0,a=n.$$scope)},[a,e]}class Y extends z{constructor(t){super(),F(this,t,X,W,j,{})}}function Z(l){let t,o="One Billion Row Challenge";return{c(){t=m("span"),t.textContent=o,this.h()},l(e){t=h(e,"SPAN",{slot:!0,"data-svelte-h":!0}),y(t)!=="svelte-i6rbpt"&&(t.textContent=o),this.h()},h(){$(t,"slot","title")},m(e,a){d(e,t,a)},p:B,d(e){e&&f(t)}}}function tt(l){let t,o="NawaMan's Java Solution";return{c(){t=m("span"),t.textContent=o,this.h()},l(e){t=h(e,"SPAN",{slot:!0,"data-svelte-h":!0}),y(t)!=="svelte-r78dol"&&(t.textContent=o),this.h()},h(){$(t,"slot","subtitle")},m(e,a){d(e,t,a)},p:B,d(e){e&&f(t)}}}function et(l){let t,o="(2-3 seconds)";return{c(){t=m("span"),t.textContent=o,this.h()},l(e){t=h(e,"SPAN",{slot:!0,"data-svelte-h":!0}),y(t)!=="svelte-a432mn"&&(t.textContent=o),this.h()},h(){$(t,"slot","subsubtitle")},m(e,a){d(e,t,a)},p:B,d(e){e&&f(t)}}}function st(l){let t,o,e,a;return t=new Y({props:{$$slots:{subsubtitle:[et],subtitle:[tt],title:[Z]},$$scope:{ctx:l}}}),e=new G({props:{nextLink:"./overview"}}),{c(){J(t.$$.fragment),o=k(),J(e.$$.fragment)},l(n){L(t.$$.fragment,n),o=q(n),L(e.$$.fragment,n)},m(n,r){M(t,n,r),d(n,o,r),M(e,n,r),a=!0},p(n,[r]){const u={};r&1&&(u.$$scope={dirty:r,ctx:n}),t.$set(u)},i(n){a||(g(t.$$.fragment,n),g(e.$$.fragment,n),a=!0)},o(n){v(t.$$.fragment,n),v(e.$$.fragment,n),a=!1},d(n){n&&f(o),O(t,n),O(e,n)}}}class rt extends z{constructor(t){super(),F(this,t,null,st,j,{})}}export{rt as component};