import React from 'react';

type CommonNavigationComponentProps = {
    data?: Record<string, any>;
};

export const CommonNavigationComponent: React.FC<CommonNavigationComponentProps> = ({ data = {} }) => {

    return (
        <div className="commonnavigationcomponent-wrapper">
            <nav className="navbar navbar-default">
        
        		<a href="/" className="navbar-brand">Brand</a>
        
        		<ul className="nav navbar-nav">
        			<li className="active"><a href="#">Home</a></li>
        			<li><a href="/list-todos.do">Todos</a></li>
        			<li><a href="http://www.in28minutes.com">In28Minutes</a></li>
        		</ul>
        
        		<ul className="nav navbar-nav navbar-right">
        			<li><a href="/logout.do">Logout</a></li>
        		</ul>
        
        	</nav>
        </div>
    );
};

export default CommonNavigationComponent;
