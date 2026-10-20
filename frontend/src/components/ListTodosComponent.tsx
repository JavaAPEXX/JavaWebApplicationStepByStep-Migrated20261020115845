import React from 'react';

type ListTodosComponentProps = {
    data?: Record<string, any>;
};

export const ListTodosComponent: React.FC<ListTodosComponentProps> = ({ data = {} }) => {
    const { errorMessage, name, todo, todos } = data;

    return (
        <div className="modern-container">
            <div className="modern-card">
                <div className="container">
        	<H1>Welcome {name}</H1>
        
        
        	<div className="modern-table-wrapper"><table className="modern-table table table-hover" className="table table-striped">
        		<caption>Your Todos are</caption>
        		<thead>
        			<th>Description</th>
        			<th>Category</th>
        			<th>Actions</th>
        		</thead>
        		<tbody>
        			{todos && todos.map((todo: any, index: number) => (
        				<tr>
        					<td>{todo.name}</td>
        					<td>{todo.category}</td>
        					<td>&nbsp;&nbsp;<a className="btn btn-danger" href="/delete-todo.do?todo={todo.name}&category={todo.category}">Delete</a></td>
        				</tr>
        			))}
        		</tbody>
        	</table></div>
        
        	<p>
        		<span>{errorMessage}</span>
        	</p>
        	<a className="btn btn-success" href="/add-todo.do">Add New Todo</a>
        </div>
            </div>
        </div>
    );
};

export default ListTodosComponent;
